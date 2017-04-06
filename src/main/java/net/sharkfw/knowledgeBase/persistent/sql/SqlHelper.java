package net.sharkfw.knowledgeBase.persistent.sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by Dustin Feurich on 03.04.2017.
 */
public class SqlHelper {

    private SqlHelper()
    {
        //static usage only
    }

    public static void importSQL(Connection conn, InputStream in) throws SQLException
    {
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");
        Statement st = null;
        conn.setAutoCommit(true);
        try
        {
            st = conn.createStatement();
            while (s.hasNext())
            {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/"))
                {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0)
                {
                    st.executeUpdate(line);
                }
            }
        }
        finally
        {
            if (st != null) st.close();

        }
    }

    public static void executeSQLCommand(Connection conn, String sql) throws SQLException
    {
        Statement st = null;
        conn.setAutoCommit(true);
        try
        {
            st = conn.createStatement();
            st.executeUpdate(sql);
        }
        finally
        {
            if (st != null) st.close();
        }
    }

    public static int getLastCreatedEntry(Connection conn, String tableName) throws SQLException
    {
        int id = -1;
        String sql = "SELECT id FROM " + tableName + " ORDER BY id DESC LIMIT 1";
        Statement st = conn.createStatement();
        try
        {
            ResultSet resultSet = st.executeQuery(sql);
            id = resultSet.getInt("id");
        }
        finally
        {
            st.close();
        }
        if (id >= 0)
        {
            return id;
        }
        else
        {
            throw new SQLException();
        }
    }

}
