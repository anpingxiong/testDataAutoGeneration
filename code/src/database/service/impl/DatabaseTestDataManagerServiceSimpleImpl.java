package database.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import database.helper.DataBaseConnectionHelper;
import database.helper.TableHelper;
import database.insert.strategy.InsertDataStrategy;
import database.pojo.Database;
import database.pojo.ForignKeyPo;
import database.service.*;

public class DatabaseTestDataManagerServiceSimpleImpl implements
		DatabaseTestDataManagerService {
	/*
	 * @see
	 * database.service.DatabaseTestDataManager#generationDataByDatabaseObject
	 * (database.po.Database) anping 下午8:43:26 think:
	 * 方案1：
	 * 1.获取数据库中的所有table2.分析表与表之间的主外键关系，先将那些没有外键联系的表格加入数据 3.对于有关联的表格则以链式的方式去
	 * 添加数据，即先分析是否有外键，然后对外键对应的表格做赋值，如果外键对应的表格仍然有 数据则递归，知道满足所有的数据为止
	 * 4.如何解决循环链接的问题呢？？ 首先分析循环链接情况，一对一，导致循环链接，那么现在随便在哪方生成数据，最后更新其他那么的数据/
	 * 多对多，如果拆解为多对一就不会出现循环问题 文章类型--》文章---》用户 可以一路递归成功 即先有用户再有文章再有文章类型 丈夫《--》妻子
	 * 那随机对任意一方赋值 学生《--》课程 拆分为辅助表---》学生 辅助表--》课程
	 * 
	 * 5.通过限制生成的测试数据在50条，即每次递归到一张表的时候，将所有的数据都生成 然后需要关联她们的则直接在已生成的数据中挑选
	 *方案2：
	 * 1.先生成每个表格数据，但是对外键占时为null
	 * 2.在生成外键
	 */
	@Override
	public void generationDataByDatabaseObject(InsertDataStrategy dataStratregy) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void clearAllTestData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebuildTestData(InsertDataStrategy dataStratregy) {
		// TODO Auto-generated method stub

	}

}
