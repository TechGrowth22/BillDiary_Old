package com.billdiary.utility;

public final class DAOConstants {

	public static final String CREATE_USER_TABLE = "create table if not exists user(id int, username varchar(20),password varchar(20),role varchar(20),primary key(id))";

}
