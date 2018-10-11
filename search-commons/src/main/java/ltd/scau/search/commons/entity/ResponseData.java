package ltd.scau.search.commons.entity;

/**
 * @author Weijie Wu
 */
public class ResponseData<T> {

    private int code;

    private String message;

    private boolean succeed;

    private T data;

    public static <T> ResponseDataBuilder<T> aData() {
        return new ResponseDataBuilder<>();
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", succeed=" + succeed +
                ", data=" + data +
                '}';
    }

    public static final class ResponseDataBuilder<T> {
        private int code = 200;
        private String message;
        private boolean succeed = true;
        private T data;

        private ResponseDataBuilder() {
        }

        public ResponseDataBuilder code(int code) {
            this.code = code;
            return this;
        }

        public ResponseDataBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseDataBuilder succeed(boolean succeed) {
            this.succeed = succeed;
            return this;
        }

        public ResponseDataBuilder data(T data) {
            this.data = data;
            return this;
        }

        public ResponseData build() {
            ResponseData responseData = new ResponseData();
            responseData.succeed = this.succeed;
            responseData.message = this.message;
            responseData.code = this.code;
            responseData.data = this.data;
            return responseData;
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public T getData() {
        return data;
    }
}
