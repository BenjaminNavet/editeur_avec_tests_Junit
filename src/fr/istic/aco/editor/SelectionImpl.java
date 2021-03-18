package fr.istic.aco.editor;

public class SelectionImpl implements Selection{

    private StringBuilder buffer;
    private int beginIndex;
    private int endIndex;


    public SelectionImpl(StringBuilder buffer) {
        this.buffer = buffer;
        this.beginIndex=0;
        this.endIndex=0;
    }


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
        //return buffer.lastIndexOf(buffer.toString());
        return buffer.length();
    }

    @Override
    public void setBeginIndex(int beginIndex) {
        if(beginIndex<0) {
            throw new IndexOutOfBoundsException(beginIndex);
        }else if(beginIndex>getEndIndex()){
            throw new IndexOutOfBoundsException(beginIndex);
        }else{
            this.beginIndex = beginIndex;
        }
    }

    @Override
    public void setEndIndex(int endIndex) {
        if(endIndex<0) {
            throw new IndexOutOfBoundsException(endIndex);
        }else if(endIndex<getBeginIndex()){
            throw new IndexOutOfBoundsException(endIndex);
        }else if(endIndex>getBufferEndIndex()){
            throw new IndexOutOfBoundsException(endIndex);
        }else{
            this.endIndex = endIndex;
        }
    }
}
