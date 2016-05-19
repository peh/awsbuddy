package aws.buddy

import org.joda.time.DateTime

class Metric implements HasJsonBody {

    DateTime forTime
    BigDecimal average
    BigDecimal maximum
    BigDecimal minimum
    BigDecimal sum
    String unit
    Type type

    static belongsTo = [instance: Instance]

    static constraints = {
    }

    @Override
    def getJsonBody() {
        [forTime: forTime,
         average: average,
         maximum: maximum,
         minimum: minimum,
         sum    : sum,
         type   : type]
    }

    static enum Type {
        CPU,
        CREDITS
    }
}
