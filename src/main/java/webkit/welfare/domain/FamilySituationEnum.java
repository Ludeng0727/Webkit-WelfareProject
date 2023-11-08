package webkit.welfare.domain;

public enum FamilySituationEnum {
    LOW_INCOME("저소득"),
    DISABLED("장애인"),
    SINGLE_PARENT("한부모·조손"),
    MULTI_CHILDREN("다자녀"),
    MULTI_CULTURE("다문화·탈북민"),
    VETARAN("보훈대상자");

    private String hangeul;

    private FamilySituationEnum(String hangeul){
        this.hangeul = hangeul;
    }

    private String getHangeul(){
        return hangeul;
    }
}
