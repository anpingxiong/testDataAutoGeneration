package database.service;

import database.insert.strategy.InsertDataStrategy;

public interface DatabaseTestDataManagerService {
	/**
	 * anping
	 * TODO 通过数据库参数对象来生成测试数据
	 * 上午8:29:35
	 */
	public void generationDataByDatabaseObject(InsertDataStrategy dataStrategy);
    /**
	 * anping
	 * TODO 清空所有的测试数据
	 * 上午8:30:57
	 */
	public void clearAllTestData();
	/**
	 * anping
	 * TODO重建所有的测试数据
	 * 上午8:31:19
	 */
	public void rebuildTestData(InsertDataStrategy dataStratregy);
}
