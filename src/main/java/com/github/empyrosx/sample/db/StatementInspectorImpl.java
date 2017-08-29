package com.github.empyrosx.sample.db;

import org.hibernate.resource.jdbc.spi.StatementInspector;

/**
 * Created by empyros on 29.08.17.
 */
public class StatementInspectorImpl implements StatementInspector {

    private static final ThreadLocal<QueryInfo> QUERY_INFO = new ThreadLocal<QueryInfo>() {
        @Override
        protected QueryInfo initialValue() {
            return new QueryInfo();
        }
    };


    @Override
    public String inspect(String sql) {
        QueryType queryType = getQueryType(sql);
        QueryInfo queryInfo = QUERY_INFO.get();
        switch (queryType) {
            case SELECT:
                queryInfo.incSelectCount();
                break;
            case INSERT:
                queryInfo.incInsertCount();
                break;
            case UPDATE:
                queryInfo.incUpdateCount();
                break;
            case DELETE:
                queryInfo.incDeleteCount();
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип запроса:" + queryType);
        }
        return sql;
    }

    protected QueryType getQueryType(String query) {
        query = query.toLowerCase();
        final String trimmedQuery = removeRedundantSymbols(query);
        final char firstChar = trimmedQuery.charAt(0);

        final QueryType type;
        switch (firstChar) {
            case 'w': // query can be started 'with'
            case 's':
                type = QueryType.SELECT;
                break;
            case 'i':
                type = QueryType.INSERT;
                break;
            case 'u':
                type = QueryType.UPDATE;
                break;
            case 'd':
                type = QueryType.DELETE;
                break;
            default:
                throw new AssertionError("Unknown QueryType");
        }
        return type;
    }

    private String removeRedundantSymbols(String query) {
        return query.replaceAll("--.*\n", "").replaceAll("\n", "").replaceAll("/\\*.*\\*/", "").trim();
    }

    public static QueryInfo getQueryInfo() {
        return QUERY_INFO.get();
    }

    public static void clear() {
        QUERY_INFO.get().clear();
    }
}
