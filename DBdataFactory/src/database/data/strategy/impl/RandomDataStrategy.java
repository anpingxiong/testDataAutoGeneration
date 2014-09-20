package database.data.strategy.impl;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import database.data.strategy.InsertDataStrategy;
import database.pojo.ColumnInfo;

/**
 * 随机生成数据策略类
 * 
 * @author anping
 */
public class RandomDataStrategy implements InsertDataStrategy {

	/*
	 * 
	 * @see
	 * database.data.strategy.InsertDataStrategy#dataGeneration(java.util.List)
	 * anping 下午4:33:02 think: 主要需要的数据的Type 和长度的约束,为避免唯一性我打算将里面所有的数据都弄成不同的
	 */
	@Override
	public List<Map<String, Object>> dataGeneration(
			List<ColumnInfo> tableColumnMessage) {

		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>(50);
		 
		return null;
	}
  
	/**
	 * 
	 * @param columnInfo
	 * @return
	 * anping
	 * TODO 通过sqlType获取数据
	 * 下午7:27:37
	 */
	public Object getDataByColumnType(ColumnInfo columnInfo) {
		Object object = null;
		switch (columnInfo.getColumnType()) {
		case Types.BIGINT: {
			 int i = new Random().nextInt();
			 object=i;
		}
			break;
		case Types.BLOB: {
			
		}
			break;// 对应二进制大对象，即对象流花了的byte[]
		case Types.BOOLEAN: {
			boolean i=new Random().nextBoolean();
			object = i;
		}
			break;
		case Types.CHAR: {
			if(columnInfo.getCharMaxLength()==0)
				object=(char) (new Random().nextInt(26)+65);
			else
				object=this.getStringByReadom(columnInfo);
		 }
			break;
		case Types.CLOB: {
			
		}
			break;// 可以存储varchar都存储不下的数据量 对应String
		case Types.DATE: {
			Date date=  new Date(10);
			object=date;
		}
			break;
		case Types.DECIMAL: {
			double  i = new Random().nextDouble();
			object=i;
		}
			break;
		case Types.DOUBLE: {
			double  i = new Random().nextDouble();
			object=i;
		}
			break;
		case Types.FLOAT: {
			object = new Random().nextFloat();
		}
			break;
		case Types.INTEGER: {
			object = new Random().nextInt();
		}
			break;
		case Types.LONGNVARCHAR: {
			 object= this.getStringByReadom(columnInfo);
		}
			break;
		case Types.LONGVARCHAR: {
			object =this.getStringByReadom(columnInfo);
		}
			break;
		case Types.NCHAR: {
			if(columnInfo.getCharMaxLength()==0)
				object=(char) (new Random().nextInt(26)+65);
			else
				object=this.getStringByReadom(columnInfo);
		}
			break;
		case Types.NCLOB: {
		}
			break;
		case Types.NUMERIC: {
			object = new Random().nextDouble();
		}
			break;
		case Types.SMALLINT: {
			object = new Random().nextInt();
		}
			break;
		case Types.TIME: {
			object= new Time(10);
		}
			break;
		case Types.TIMESTAMP: {
			object = new Timestamp(10);
		}
			break;
		case Types.TINYINT: {
			object= new Random().nextInt();
		}
			break;
		case Types.VARBINARY: {
		
		}
			break;
		case Types.VARCHAR: {
			if(columnInfo.getCharMaxLength()==0)
				object=(char) (new Random().nextInt(26)+65);
			else
				object=this.getStringByReadom(columnInfo);
		}
			break;
		default: {
		}

		}

		return object;
	}

	//返回String 不过全部是字符字母组成
	private  String getStringByReadom(ColumnInfo columnInfo){
		int size= columnInfo.getCharMaxLength();
		char[] data = new char[size];
		for(int i=0;i<size;i++){
			int flag =new Random().nextInt(1);
			if(0==flag)
				i= (char) (new Random().nextInt(26)+65);
			else
			    data[i]= (char) (new Random().nextInt(26)+65);
		}
		return new String(data);
	}
}
