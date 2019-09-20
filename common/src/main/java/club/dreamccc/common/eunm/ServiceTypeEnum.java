package club.dreamccc.common.eunm;

/**
 * <h2>ServiceTypeEnum</h2>
 * <p>请添加描述</p>
 *
 * @author Daizc
 * @date 2019/9/20
 */
public enum ServiceTypeEnum {

    /**
     * 服务端
     */
    SERVER("服务端",'S'),
    /**
     * 控制端
     */
    CONTROL("控制端",'C'),
    /**
     * 管理端
     */
    MANAGER("管理端",'M');


    private String typeName;
    private Character typeChar;

    ServiceTypeEnum(String typeName, Character typeChar) {
        this.typeName = typeName;
        this.typeChar = typeChar;
    }

    public String getTypeName() {
        return typeName;
    }


    public Character getTypeChar() {
        return typeChar;
    }

}
