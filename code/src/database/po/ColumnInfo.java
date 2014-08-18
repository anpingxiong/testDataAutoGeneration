package database.po;

import java.sql.Types;

public class ColumnInfo {
	private String columnName;
	private int columnType;
	private boolean nullAble;
	private boolean autoIncreseAble;
	private int charMaxLength;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public boolean isNullAble() {
		return nullAble;
	}

	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}

	public boolean isAutoIncreseAble() {
		return autoIncreseAble;
	}

	public void setAutoIncreseAble(boolean autoIncreseAble) {
		this.autoIncreseAble = autoIncreseAble;
	}

	public int getCharMaxLength() {
		return charMaxLength;
	}

	public void setCharMaxLength(int charMaxLength) {
		this.charMaxLength = charMaxLength;
	}

}
