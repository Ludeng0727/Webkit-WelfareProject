package webkit.welfare.domain;

public enum LifeCycleEnum {
    INFANT("영유아"),
    CHILD("아동"),
    TEEN("청소년"),
    YOUTH("청년"),
    MIDDLE_AGE("중장년"),
    OLD_AGE("노년");

    private String hangeul;

    private LifeCycleEnum(String hangeul){
        this.hangeul = hangeul;
    }

    public String getHangeul(){
        return hangeul;
    }
}
