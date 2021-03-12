package fr.istic.aco.editor;

public class SelectionImpl implements Selection{

    private StringBuilder buffer;
    private int beginIndex = 0;
    private int endIndex = 0;


    public SelectionImpl(StringBuilder buffer) {
        this.buffer = buffer;
    }

// test


    @Override
    public int getBeginIndex() {
        return beginIndex;
    }

    @Override
    public int getEndIndex() {
        return endIndex;
    }

    @Override
    public int getBufferBeginIndex() {
        return 0;
    }

    @Override
    public int getBufferEndIndex() {
        return buffer.lastIndexOf(buffer.toString());
    }

    @Override
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    @Override
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
