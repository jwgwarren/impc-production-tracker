package uk.ac.ebi.impc_prod_tracker.service.biology.plan.crispr;

import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.CrisprAttempt;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.CrisprAttemptRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.delivery_type.DeliveryMethodType;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.nuclease.Nuclease;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.nuclease.NucleaseRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.genotype_primer.GenotypePrimer;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.genotype_primer.GenotypePrimerRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.mutagenesis_donor.MutagenesisDonor;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.mutagenesis_donor.MutagenesisDonorRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.assay.assay_type.AssayType;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.assay.assay_type.AssayTypeRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.delivery_type.DeliveryMethodTypeRepository;

import java.util.List;
import java.util.Optional;

@Component
public class CrisprAttemptServiceImpl implements CrisprAttemptService
{
    private CrisprAttemptRepository crisprAttemptRepository;
    private GenotypePrimerRepository genotypePrimerRepository;
    private NucleaseRepository nucleaseRepository;
    private MutagenesisDonorRepository mutagenesisDonorRepository;
    private AssayTypeRepository assayTypeRepository;
    private DeliveryMethodTypeRepository deliveryTypeRepository;

    public CrisprAttemptServiceImpl(
        CrisprAttemptRepository crisprAttemptRepository,
        GenotypePrimerRepository genotypePrimerRepository,
        NucleaseRepository nucleaseRepository,
        MutagenesisDonorRepository mutagenesisDonorRepository,
        AssayTypeRepository assayTypeRepository,
        DeliveryMethodTypeRepository deliveryTypeRepository)
    {
        this.crisprAttemptRepository = crisprAttemptRepository;
        this.genotypePrimerRepository = genotypePrimerRepository;
        this.nucleaseRepository = nucleaseRepository;
        this.mutagenesisDonorRepository = mutagenesisDonorRepository;
        this.assayTypeRepository = assayTypeRepository;
        this.deliveryTypeRepository = deliveryTypeRepository;
    }

    @Override
    public Optional<CrisprAttempt> getCrisprAttemptById(Long planId)
    {
        return crisprAttemptRepository.findById(planId);
    }

    public List<GenotypePrimer> getGenotypePrimersByCrisprAttempt(CrisprAttempt crisprAttempt)
    {
        return genotypePrimerRepository.findAllByCrisprAttempt(crisprAttempt);
    }

    public List<Nuclease> getNucleasesByCrisprAttempt(CrisprAttempt crisprAttempt)
    {
        return nucleaseRepository.findAllByCrisprAttempt(crisprAttempt);
    }

    public List<MutagenesisDonor> getMutagenesisDonorsByCrisprAttempt(CrisprAttempt crisprAttempt)
    {
        return mutagenesisDonorRepository.findAllByCrisprAttempt(crisprAttempt);
    }

    @Override
    public AssayType getAssayTypeByName(String assayTypeName)
    {
        return assayTypeRepository.findByName(assayTypeName);
    }

    @Override
    public DeliveryMethodType getDeliveryTypeByName(String deliveryTypeName)
    {
        return deliveryTypeRepository.findByName(deliveryTypeName);
    }
}
