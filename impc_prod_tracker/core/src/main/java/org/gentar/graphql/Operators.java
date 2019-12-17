package org.gentar.graphql;

public enum Operators
{
    AND("_and"),
    OR("_or"),
    EQ("_eq"),
    GT("_gt"),
    GTE("_gte"),
    LIKE("_like"),
    NLIKE("_nlike"),
    ILIKE("_ilike"),
    NILIKE("_nilike"),
    IN("_in"),
    IS_NULL("_is_null"),
    LT("_lt"),
    LTE("_lte"),
    NEQ("_neq"),
    NIN("_nin"),
    NSIMILAR("_nsimilar"),
    SIMILAR("_similar");

    private String name;

    Operators(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
