package com.penghai.css.management.business;

import java.util.Map;

import com.penghai.css.management.model.databaseModel.Schema;

public interface IQueryIfExistSchema {
	Map<String, Object> queryIfExistSchema(Schema schema);
}
