package zz;


import java.io.Serializable;

public class ZAny implements Serializable {
    public <T> T asInstance(Class<T> cls) {
        if(null == cls)
            return null;
        return (T)this;
    }

    public <T> boolean isInstance(Class<T> cls) {
        return null != cls && cls.isAssignableFrom(this.getClass());
    }
}
