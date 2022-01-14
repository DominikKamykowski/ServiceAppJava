package Test;

import App.Convert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvertTest {

    @Test
    void baudRateConvertProperly() {
        /*! \brief Test jednostkowy sprawdzający poprawność konwersji danych klasy Convert.
         *
         */
        //given
        int cbIndex = 2;
        Convert convert = new Convert();
        //when
        int result = convert.baudRateConvert(cbIndex);

        //then
        Assertions.assertEquals(4800,result);
    }

    @Test
    void baudRateConvertIllegalArgumentException(){
        /*! \brief Test jednostkowy sprawdzający poprawność zgłaszania wyjatku IllegalArgumentException.
         *
         */
        //given
        int cbIndex = 10;
        Convert convert = new Convert();
        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class,() -> convert.baudRateConvert(cbIndex));
    }
}