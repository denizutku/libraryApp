
package springboot.libraryApp.models.GoogleAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPrice_ {

    @SerializedName("amountInMicros")
    @Expose
    private int amountInMicros;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    public int getAmountInMicros() {
        return amountInMicros;
    }

    public void setAmountInMicros(int amountInMicros) {
        this.amountInMicros = amountInMicros;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
