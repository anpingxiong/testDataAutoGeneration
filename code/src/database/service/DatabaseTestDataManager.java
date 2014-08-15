package database.service;

import database.po.Database;

public interface DatabaseTestDataManager {
	/**
	 * @param database
	 * anping
	 * TODO 通过数据库参数对象来生成测试数据
	 * 上午8:29:35
	 */
	public void generationDataByDatabaseObject(Database database);
    /**
     * @param database
	 * anping
	 * TODO 清空所有的测试数据
	 * 上午8:30:57
	 */
	public void clearAllTestData(Database database);
	/**
	 * @param database
	 * anping
	 * TODO重建所有的测试数据
	 * 上午8:31:19
	 */
	public void rebuildTestData(Database database);
}
