package Thread;

import java.util.concurrent.Exchanger;
import java.text.DecimalFormat;

public class ComplexThread extends Thread {
    private Double xInitial;
    private Double yInitial;
    private Integer n;
    private Integer k;
    private Exchanger<String> exchanger;

    public ComplexThread(Double x, Double y, Integer n, Integer k, Exchanger<String> exchanger) {
        this.xInitial = x;
        this.yInitial = y;
        this.n = n;
        this.k = k;
        this.exchanger = exchanger;
    }

    public void run() {
        Double module = Math.pow((Math.pow(xInitial, 2) + Math.pow(yInitial, 2)), 1.0 / 2);
        Double argument = Math.atan(yInitial / xInitial);
        Double xResult = Math.pow(module, 1.0 / n) * Math.cos((argument + 2 * Math.PI * k) / n);
        Double yResult = Math.pow(module, 1.0 / n) * Math.sin((argument + 2 * Math.PI * k) / n);
        DecimalFormat formatter = new DecimalFormat("#0.000");
        String sign;
        if (yResult < 0)
            sign = " ";
        else
            sign = " + ";
        String result = formatter.format(xResult) + sign + formatter.format(yResult) + "i";
        try {
            exchanger.exchange(result);
        } catch (Exception ex) {
        }
    }
}
