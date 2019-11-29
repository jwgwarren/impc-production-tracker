package org.gentar.biology.project;

public enum PlanTypes
{
    PRODUCTION("Production"),
    PHENOTYPING("Phenotyping");

    private String typeName;

    PlanTypes(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTypeName()
    {
        return typeName;
    }
}
