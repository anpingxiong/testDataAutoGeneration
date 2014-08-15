package database.po;

/**
 * 
 * @author anping
 * 记录外键所在的表格和外键信息
 */
public class ForignKeyPo {
	private String forignKeyName;
	private String forignKeyTable;
	public String getForignKeyName() {
		return forignKeyName;
	}
	public void setForignKeyName(String forignKeyName) {
		this.forignKeyName = forignKeyName;
	}
	public String getForignKeyTable() {
		return forignKeyTable;
	}
	public void setForignKeyTable(String forignKeyTable) {
		this.forignKeyTable = forignKeyTable;
	}
	
}
