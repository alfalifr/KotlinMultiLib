package sidev.lib;

import kotlin.Pair;
import kotlin.sequences.SequencesKt;
import org.junit.Test;
import sidev.lib.collection.lazy_list._LazyListFunKt;

import java.util.Iterator;

import static kotlin.sequences.SequencesKt.*;
import static kotlin.TuplesKt.*;

public class SampleTestJava {

    @Test
    public void lazyListTest(){
        var lazi1 = _LazyListFunKt.<String, Integer>lazyMapOf();
        var seq1 = sequenceOf(to("Kamu",1), to("Dia",10), to("Makan",5));

//        lazi1.addIterator(seq1.iterator());

//        this.<String>ad("kll");
    }

    <T, O> O ad(T a){
        return (O) a;
    }
}
