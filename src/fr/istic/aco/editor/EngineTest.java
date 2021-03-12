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

    private void todo() {
        fail("Unimplemented test");
    }

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

    @Test
    @DisplayName("Selection must be ABC")
    void getSelectionABC() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(engine.getSelection().getBufferBeginIndex());
        engine.getSelection().setEndIndex(2);
        Selection selection = engine.getSelection();
        engine.copySelectedText();
        assertEquals("ABC",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Selection must be BCD")
    void getSelectionBCD() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(1);
        engine.getSelection().setEndIndex(engine.getSelection().getBufferEndIndex());
        Selection selection = engine.getSelection();
        engine.copySelectedText();
        assertEquals("BCD",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Selection must be ABCD")
    void getSelectionABCD() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(engine.getSelection().getBufferBeginIndex());
        engine.getSelection().setEndIndex(engine.getSelection().getBufferEndIndex());
        Selection selection = engine.getSelection();
        engine.copySelectedText();
        assertEquals("ABCD",engine.getClipboardContents());
    }

    @Test
    @DisplayName("Selection must be BC")
    void getSelectionBC() {
        engine.insert("ABCD");
        engine.getSelection().setBeginIndex(1);
        engine.getSelection().setEndIndex(2);
        Selection selection = engine.getSelection();
        engine.copySelectedText();
        assertEquals("BC",engine.getClipboardContents());
    }


    @Test
    @DisplayName("Buffer doit contenir la string 'ABCD'")
    void getBufferContentsWithString() {
        engine.insert("ABCD");
        String buffContents=engine.getBufferContents();
        assertEquals("ABCD",buffContents);
        assertEquals(3,engine.getSelection().getBufferEndIndex());
    }
}
