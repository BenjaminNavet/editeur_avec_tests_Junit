package fr.istic.aco.editor;

public class EngineImpl implements Engine {

    private StringBuilder buffer = new StringBuilder();
    private String clipboard = "";
    private Selection selection = new SelectionImpl(buffer);

    /**
     * Provides access to the selection control object
     *
     * @return the selection object
     */
    @Override
    public Selection getSelection() {
        // TODO
        return selection;
    }

    /**
     * Provides the whole contents of the buffer, as a string
     *
     * @return a copy of the buffer's contents
     */
    @Override
    public String getBufferContents() {
        return buffer.toString();
    }

    /**
     * Provides the clipboard contents
     *
     * @return a copy of the clipboard's contents
     */
    @Override
    public String getClipboardContents() {
        // TODO
        return clipboard;
    }

    /**
     * Removes the text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    @Override
    public void cutSelectedText() {
        copySelectedText();
        delete();
    }

    /**
     * Copies the text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    @Override
    public void copySelectedText() {
        clipboard = getBufferContents().substring(selection.getBeginIndex(),selection.getEndIndex());
    }

    /**
     * Replaces the text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    @Override
    public void pasteClipboard() {
        buffer.replace(selection.getBeginIndex(),selection.getEndIndex(),clipboard);
    }

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     *
     * @param s the text to insert
     */
    @Override
    public void insert(String s) {
        buffer.insert(selection.getBeginIndex(),s);
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        buffer.delete(selection.getBeginIndex(),selection.getEndIndex());
    }
}
