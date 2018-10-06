package ltd.scau.search.crawler.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Weijie Wu
 */
public class ExecuteResult<T> implements Serializable {

    private static final long serialVersionUID = -1179641925634453244L;

    private final Boolean succeed;
    private final T product;
    private final String message;
    private final Date executeDate;

    private ExecuteResult(Boolean succeed, T product, String message, Date executeDate) {
        this.succeed = succeed;
        this.product = product;
        this.message = message;
        this.executeDate = executeDate;
    }

    public static ExecuteResultBuilder aResult() {
        return new ExecuteResultBuilder<>();
    }

    public static final class ExecuteResultBuilder<T> {
        private Boolean succeed;
        private T product;
        private String message;
        private Date executeDate = new Date();

        public ExecuteResultBuilder succeed(Boolean succeed) {
            this.succeed = succeed;
            return this;
        }

        public ExecuteResultBuilder product(T product) {
            this.product = product;
            return this;
        }

        public ExecuteResultBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExecuteResultBuilder executeDate(Date executeDate) {
            this.executeDate = executeDate;
            return this;
        }

        public ExecuteResult build() {
            return new ExecuteResult(succeed, product, message, executeDate);
        }
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public T getProduct() {
        return product;
    }

    public String getMessage() {
        return message;
    }

    public Date getExecuteDate() {
        return executeDate;
    }
}
