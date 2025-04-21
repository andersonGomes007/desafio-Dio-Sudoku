package model;

public enum EnumStatus {
	
	NON_STARTED("não iniciado"),
    INCOMPLETE("incompleto"),
    COMPLETE("completo");

    private String label;

    EnumStatus(final String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
