package utn.dao.mysql;

public class MySQLUtils {

    public static String BASE_USER_QUERY = "select * from users u inner join cities c inner join provinces as pro on pro.id_province = c.id_province_fk on u.id_city_fk = c.id_city ";
    public static String GET_BY_ID_USER_QUERY = BASE_USER_QUERY + " where u.id_user = ?";
    public static String GET_BY_USERNAME_USER_QUERY = BASE_USER_QUERY + "  where username = ? and pwd=?";
    public static final String INSERT_USER_QUERY = "insert into users(first_name,surname,dni,birthdate,username,pwd,email,user_type,user_status,id_city_fk) values (?,?,?,?,?,?,?,?,?,?);";
    public static final String UPDATE_USER_STATUS_QUERY = "update users set user_status=2 where id_user =?";
    public static final String UPDATE_USER_QUERY="update users set first_name=?, surname=?, dni=?, birthdate=?, username=?,pwd=?,email=?,user_type=?,user_status=?,id_city_fk=? where id_user=?";

    public static final String BASE_CITY_QUERY = "select * from cities";
    public static final String GET_CITY_BY_PREFIX = BASE_CITY_QUERY + " where prefix = ?";
    public static final String GET_MOST_CALLED_NUMBER="select f.line_number_to ,u.first_name,u.surname " +
                                                        "from users as u \n" +
                                                        "join (select line_number_to, id_line_number_from_fk\n" +
                                                        "\t  from phonecalls\n" +
                                                        "\t  where line_number_from=?\n" +
                                                        "\t  group by line_number_to\n" +
                                                        "\t  order by count(line_number_to) desc\n" +
                                                        "\t  limit 1) as f\n" +
                                                        "on f.id_line_number_from_fk = u.id_user";
}
