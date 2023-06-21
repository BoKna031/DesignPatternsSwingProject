package model.entity;

public enum LogType {
    ADD("Add"),
    REMOVE("Remove"),
    MODIFY_TO ("Modify to"),
    SELECT ("Select"),
    DESELECT("Deselect"),
    TO_FRONT("To front"),
    TO_BACK("To back"),
    BRING_BACK("Bring back"),
    BRING_FRONT("Bring front"),
    UNDO("Undo"),
    REDO("Redo");
    private final String additionalValue;

    LogType(String additionalValue) {
        this.additionalValue = additionalValue;
    }

    @Override
    public String toString() {
        return additionalValue;
    }
}
