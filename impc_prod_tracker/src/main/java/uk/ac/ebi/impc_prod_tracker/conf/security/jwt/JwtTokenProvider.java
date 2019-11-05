/*******************************************************************************
 * Copyright 2019 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *******************************************************************************/
package uk.ac.ebi.impc_prod_tracker.conf.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.conf.exceptions.UserOperationFailedException;
import uk.ac.ebi.impc_prod_tracker.conf.security.AapSystemSubject;
import uk.ac.ebi.impc_prod_tracker.conf.security.AuthorizationHeaderReader;
import uk.ac.ebi.impc_prod_tracker.conf.security.PublicKeyProvider;
import uk.ac.ebi.impc_prod_tracker.conf.security.SystemSubject;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

/**
 * This class manages the creation and validation of JWT.
 * @author Mauricio Martinez
 */
@Component
public class JwtTokenProvider
{
    static final String INVALID_TOKEN_MESSAGE = "Expired or invalid JWT token.";
    private static final String INVALID_TOKEN_DEBUG_MESSAGE =
        "Tokens expire after a while (usually 1 hour), please create a new one. Also check that you"
            + " are using the whole token in the authentication header. Contact your administrator"
            + "if after checking this you keep receiving the same error.";
    static final String NULL_EMPTY_TOKEN_MESSAGE = "The token cannot be null or empty.";

    private PublicKeyProvider publicKeyProvider;
    private AapSystemSubject aapSystemSubject;

    private AuthorizationHeaderReader authorizationHeaderReader = new AuthorizationHeaderReader();

    public JwtTokenProvider(
        PublicKeyProvider publicKeyProvider, AapSystemSubject aapSystemSubject)
    {
        this.publicKeyProvider = publicKeyProvider;
        this.aapSystemSubject = aapSystemSubject;
    }

    Authentication getAuthentication(String token)
    {
        if (token == null || token.isEmpty())
        {
            throw new UserOperationFailedException(NULL_EMPTY_TOKEN_MESSAGE);
        }
        SystemSubject systemSubject = getSystemSubject(token);
        return new UsernamePasswordAuthenticationToken(systemSubject, "", null);
    }

    public SystemSubject getSystemSubject(String token)
    {
        return aapSystemSubject.buildSystemSubjectByClaims(getClaims(token));
    }

    Claims getClaims(String token)
    {
        return Jwts.parser()
            .setSigningKeyResolver(getSigningKeyResolver())
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * Obtain the token from the Authorization header.
     * @param req Request.
     * @return String with the token.
     */
    String resolveToken(HttpServletRequest req)
    {
        return authorizationHeaderReader.getAuthorizationToken(req);
    }

    /**
     * Validates that the JWT is valid
     * @param token The token.
     * @return True if token is valid. False otherwise.
     */
    boolean validateToken(String token)
    {
        try
        {
            Jws<Claims> claims = Jwts.parser()
                .setSigningKeyResolver(getSigningKeyResolver())
                .parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date()))
            {
                return false;
            }

            return true;
        }
        catch (JwtException | IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
            throw new UserOperationFailedException(INVALID_TOKEN_MESSAGE, INVALID_TOKEN_DEBUG_MESSAGE);
        }
    }

    private SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter()
    {
        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims)
        {
            String issuer = claims.getIssuer();
            return publicKeyProvider.getPublicKey(issuer);
        }
    };

    public SigningKeyResolver getSigningKeyResolver()
    {
        return signingKeyResolver;
    }
}
