package fr.istic.aco.editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {

    private Engine engine;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }


/* tests à l'initialisation */
    @Test
    @DisplayName("Selection must be empty after initialisation")
    void getSelectionInit() {
        Selection selection = engine.getSelection();
        assertEquals(selection.getBufferBeginIndex(),selection.getBeginIndex());
        assertEquals("",engine.getBufferContents());
    }

    @Test
    @DisplayName("Buffer must be empty after initialisation")
    void getBufferContentsInit() {
        assertEquals("",engine.getBufferContents());
    }

    @Test
    @DisplayName("BufferBeginIndex must be 0 after initialisation")
    void getBufferBeginIndexInit() {
        assertEquals(0,engine.getSelection().getBufferBeginIndex());
    }

    @Test
    @DisplayName("BufferEndIndex must be 0 after initialisation")
    void getBufferEndIndexInit() {
        assertEquals(0,engine.getSelection().getBufferEndIndex());
    }

    @Test
    @DisplayName("Clipboard must be empty after initialisation")
    void getClipboardContentsInit() {
        assertEquals("",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Clipboard must be empty after copy text after initialisation")
    void copySelectedTextInit() {
        engine.copySelectedText();
        assertEquals("",engine.getClipboardContents());
    }


    @Test
    @DisplayName("Clipboard and Buffer must be empty after cut text after initialisation")
    void cutSelectedTextInit() {
        engine.cutSelectedText();
        assertEquals("",engine.getClipboardContents());
        assertEquals("",engine.getBufferContents());
    }



    @Test
    @DisplayName("Buffer must be empty after paste text after initialisation")
    void pasteClipboardInit() {
        engine.pasteClipboard();
        assertEquals("",engine.getBufferContents());
    }


    /* tests fonctionnels*/

    @Test
    @DisplayName("Selection must be ABC")
    void getSelectionABC() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(engine.getSelection().getBufferBeginIndex());
        engine.getSelection().setEndIndex(3);
        engine.copySelectedText();
        assertEquals("ABC",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Selection must be BCD")
    void getSelectionBCD() {
        engine.insert("ABCD");
        engine.getSelection().setEndIndex(engine.getSelection().getBufferEndIndex());
        engine.getSelection().setBeginIndex(1);

        engine.copySelectedText();
        assertEquals("BCD",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Selection must be ABCD")
    void getSelectionABCD() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(engine.getSelection().getBufferBeginIndex());
        engine.getSelection().setEndIndex(engine.getSelection().getBufferEndIndex());
        engine.copySelectedText();
        assertEquals("ABCD",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Selection must be BC")
    void getSelectionBC() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(1);
        engine.getSelection().setEndIndex(3);
        engine.copySelectedText();
        assertEquals("BC",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Buffer must contain string 'ABCD'")
    void getBufferContentsWithString() {
        engine.insert("ABCD");
        String buffContents=engine.getBufferContents();
        assertEquals("ABCD",buffContents);
    }


    @Test
    @DisplayName("BufferBeginIndex must be 0")
    void getBufferBeginIndex() {
        engine.insert("ABCD");
        assertEquals(0,engine.getSelection().getBufferBeginIndex());
    }



    @Test
    @DisplayName("BufferEndIndex must be 4")
    void getBufferEndIndex() {
        engine.insert("ABCD");
        assertEquals(4,engine.getSelection().getBufferEndIndex());
    }


    @Test
    @DisplayName("Verify if setBeginIndex(beginIndex) and setEndIndex(endIndex) works correctly")
    void setIndexVerif() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(2);
        engine.getSelection().setEndIndex(3);
        assertEquals(2,engine.getSelection().getBeginIndex());
        assertEquals(3,engine.getSelection().getEndIndex());
    }



    @Test
    @DisplayName("Delete all content in buffer")
    void delete() {
        assertEquals("",engine.getBufferContents());
        engine.insert("abcd");
        assertEquals("abcd", engine.getBufferContents());
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(4);
        engine.delete();
        assertEquals("",engine.getBufferContents());
        assertEquals(0, engine.getSelection().getBufferEndIndex());
        assertEquals(0, engine.getSelection().getBufferBeginIndex());
    }

    @Test
    @DisplayName("Delete a selection of content in buffer")
    void deleteSelectionOfContent() {
        assertEquals("",engine.getBufferContents());
        engine.insert("abcd");
        assertEquals("abcd", engine.getBufferContents());

        // ici on est obligé de modifier le endIndex AVANT le beginIndex sinon erreur car endIndex<BeginIndex (EndIndex initialement à 0)
        engine.getSelection().setEndIndex(4);
        engine.getSelection().setBeginIndex(2);
        engine.delete();
        assertEquals("ab",engine.getBufferContents());
        assertEquals(2, engine.getSelection().getBufferEndIndex());
        assertEquals(0, engine.getSelection().getBufferBeginIndex());
    }


    @Test
    @DisplayName("Verify if getClipboardContents and copySelectedText work correctly")
    void verifyGetClipboardContents(){
        assertEquals("",engine.getClipboardContents());
        assertEquals("",engine.getBufferContents());
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(2);
        engine.getSelection().setEndIndex(3);
        engine.copySelectedText();
        assertEquals("C",engine.getClipboardContents());
        engine.getSelection().setEndIndex(4);
        engine.getSelection().setBeginIndex(2);
        engine.copySelectedText();
        assertEquals("CD",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Verify if cutSelectedText works correctly")
    void verifyCutSelectedText(){
        assertEquals("",engine.getClipboardContents());
        assertEquals("",engine.getBufferContents());
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(2);
        engine.getSelection().setEndIndex(3);
        engine.cutSelectedText();
        assertEquals("C",engine.getClipboardContents());
        assertEquals("ABD",engine.getBufferContents());
        engine.getSelection().setEndIndex(3);
        engine.getSelection().setBeginIndex(1);
        engine.cutSelectedText();
        assertEquals("BD",engine.getClipboardContents());
        assertEquals("A",engine.getBufferContents());
    }


    @Test
    @DisplayName("Buffer must have new pasted content")
    void VerifyCopyAndPasteClipboard() {
        assertEquals("",engine.getBufferContents());
        engine.insert("abcd");
        assertEquals("abcd", engine.getBufferContents());
        // ici on est obligé de modifier le endIndex AVANT le beginIndex sinon erreur car endIndex<BeginIndex (EndIndex initialement à 0)
        engine.getSelection().setEndIndex(4);
        engine.getSelection().setBeginIndex(2);
        engine.copySelectedText();
        // on positionne la selection en fin de buffer
        engine.getSelection().setBeginIndex(4);
        engine.pasteClipboard();
        assertEquals("abcdcd",engine.getBufferContents());
        assertEquals(6, engine.getSelection().getBufferEndIndex());
        assertEquals(0, engine.getSelection().getBufferBeginIndex());
        assertEquals("cd", engine.getClipboardContents());
    }


    @Test
    @DisplayName("Verify if pasteClipboard works correctly")
    void verifyCutAndPasteClipboard(){
        assertEquals("",engine.getClipboardContents());
        assertEquals("",engine.getBufferContents());
        engine.insert("ABCD");

        // Cut letter "C"
        engine.getSelection().setBeginIndex(2);
        engine.getSelection().setEndIndex(3);
        engine.cutSelectedText();
        assertEquals("C",engine.getClipboardContents());
        assertEquals("ABD",engine.getBufferContents());

        // Modify selection to paste the clipboard at the end of buffer
        engine.getSelection().setEndIndex(engine.getSelection().getBufferEndIndex());
        engine.getSelection().setBeginIndex(engine.getSelection().getBufferEndIndex());
        engine.pasteClipboard();
        assertEquals("C",engine.getClipboardContents());
        assertEquals("ABDC",engine.getBufferContents());

        // Cut letter "C"
        engine.getSelection().setEndIndex(engine.getSelection().getBufferEndIndex());
        engine.getSelection().setBeginIndex(engine.getSelection().getBufferEndIndex()-1);
        engine.cutSelectedText();
        assertEquals("C",engine.getClipboardContents());
        assertEquals("ABD",engine.getBufferContents());

        // Modify selection to paste the clipboard between letters "A" and "B"
        engine.getSelection().setBeginIndex(1);
        engine.getSelection().setEndIndex(1);
        engine.pasteClipboard();
        assertEquals("C",engine.getClipboardContents());
        assertEquals("ACBD",engine.getBufferContents());

        // Cut SubString "BD"
        engine.getSelection().setEndIndex(4);
        engine.getSelection().setBeginIndex(2);
        engine.cutSelectedText();
        assertEquals("BD",engine.getClipboardContents());
        assertEquals("AC",engine.getBufferContents());

        // Modify selection to paste the clipboard between letters "A" and "C"
        engine.getSelection().setBeginIndex(1);
        engine.getSelection().setEndIndex(1);
        engine.pasteClipboard();
        assertEquals("BD",engine.getClipboardContents());
        assertEquals("ABDC",engine.getBufferContents());

        // Cut SubString "BD"
        engine.getSelection().setEndIndex(3);
        engine.getSelection().setBeginIndex(1);
        engine.cutSelectedText();
        assertEquals("BD",engine.getClipboardContents());
        assertEquals("AC",engine.getBufferContents());

        // Modify selection to paste the clipboard after letter "C"
        engine.getSelection().setEndIndex(2);
        engine.getSelection().setBeginIndex(2);
        engine.pasteClipboard();
        assertEquals("BD",engine.getClipboardContents());
        assertEquals("ACBD",engine.getBufferContents());

        // Cut SubString "BD"
        engine.getSelection().setEndIndex(4);
        engine.getSelection().setBeginIndex(2);
        engine.cutSelectedText();
        assertEquals("BD",engine.getClipboardContents());
        assertEquals("AC",engine.getBufferContents());

        // Modify selection to paste the clipboard before letter "A"
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(0);
        engine.pasteClipboard();
        assertEquals("BD",engine.getClipboardContents());
        assertEquals("BDAC",engine.getBufferContents());
    }

    @Test
    @DisplayName("Verify if insert works correctly")
    void verifyInsert(){
        assertEquals("",engine.getBufferContents());

        engine.insert("ABCD");
        assertEquals("ABCD",engine.getBufferContents());

        engine.insert(" EFGH");
        assertEquals("ABCD EFGH",engine.getBufferContents());

        engine.getSelection().setBeginIndex(4);
        engine.getSelection().setEndIndex(4);
        engine.insert(" coucou");
        assertEquals("ABCD coucou EFGH",engine.getBufferContents());

        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(4);
        engine.insert("EFGH");
        assertEquals("EFGH coucou EFGH",engine.getBufferContents());
    }



    /* tests de robustesse */
    @Test
    @DisplayName("IndexOutOfBoundsException for setBeginIndex(-1)")
    void setBeginIndexNeg() {
        assertThrows(IndexOutOfBoundsException.class,()->engine.getSelection().setBeginIndex(-1));
    }

    @Test
    @DisplayName("IndexOutOfBoundsException for setEndIndex(-1)")
    void setEndIndexNeg() {
        assertThrows(IndexOutOfBoundsException.class,()->engine.getSelection().setEndIndex(-1));
    }

    @Test
    @DisplayName("IndexOutOfBoundsException for setEndIndex()>getBufferEndIndex() with buffer empty")
    void setEndIndexOut() {
        assertThrows(IndexOutOfBoundsException.class,()->engine.getSelection().setEndIndex(1));
    }


    @Test
    @DisplayName("IndexOutOfBoundsException for setEndIndex()<getBeginIndex()")
    void setEndIndexInf() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(2);
        engine.getSelection().setEndIndex(3);
        assertThrows(IndexOutOfBoundsException.class,()->engine.getSelection().setEndIndex(1));
    }

    @Test
    @DisplayName("IndexOutOfBoundsException for setBeginIndex()>getEndIndex()")
    void setBeginIndexSup() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(2);
        engine.getSelection().setEndIndex(3);
        assertThrows(IndexOutOfBoundsException.class,()->engine.getSelection().setBeginIndex(4));
    }

    @Test
    @DisplayName("Exception for setBeginIndex('4')")
    void setBeginIndexWrongType() { assertThrows(IndexOutOfBoundsException.class,()->engine.getSelection().setBeginIndex('4')); }

    @Test
    @DisplayName("Exception for setEndIndex('4')")
    void setEndIndexWrongType() {
        assertThrows(IndexOutOfBoundsException.class,()->engine.getSelection().setEndIndex('4'));
    }






}
