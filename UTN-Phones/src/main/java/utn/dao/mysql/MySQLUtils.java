package utn.dao.mysql;

public class MySQLUtils {

    protected static String BASE_USER_QUERY = "select * from users u inner join cities c inner join provinces pro on pro.id = c.id_province on u.id_city = c.id ";
    protected static String GET_BY_ID_USER_QUERY = BASE_USER_QUERY + " where u.id = ?";
    protected static String GET_BY_USERNAME_USER_QUERY = BASE_USER_QUERY + "  where username = ? and pwd=?";
    protected static final String INSERT_USER_QUERY = "insert into users(first_name,surname,dni,birthdate,username,pwd,email,user_type,user_status,id_city) values (?,?,?,?,?,?,?,?,?,?);";
    protected static final String UPDATE_USER_STATUS_QUERY = "update users set user_status=2 where id =?";
    protected static final String UPDATE_USER_QUERY="update users set first_name=?, surname=?, dni=?, birthdate=?, username=?,pwd=?,email=?,user_type=?,user_status=?,id_city=? where id=?";

    protected static final String BASE_CITY_QUERY = "select * from cities";
    protected static final String GET_CITY_BY_PREFIX = BASE_CITY_QUERY + " where prefix = ?";
}
