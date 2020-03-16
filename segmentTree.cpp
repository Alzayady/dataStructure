struct SegmentTree{
    vector <long long >  segmentTree;
    long long *arr;
    int size;
    SegmentTree(int n ) : size(n)
    {
        segmentTree.resize(1<<20);
    }
    SegmentTree( )
    {
        segmentTree.resize(1<<20);
    }
    void setarray(long long *a, int n ){
        arr=a;
        setSize(n);
    }
    void setSize(int n){
        size=n;
    }
    void build(int node , int start ,  int en ){
        if(start==en){
            segmentTree[node]=arr[start];
            return;
        }
        int mid =(start+en)/2;
        build(node*2,start,mid);
        build(node*2+1,mid+1,en);
        segmentTree[node]=segmentTree[node*2]+segmentTree[node*2+1];
    }

    long long getSum(int start , int en){
        return getSum(1,1,size,start,en);
    }

    long long  getSum(int node , int start , int en , int startOfInterval , int endOfInterval){
        if(startOfInterval<=start&&endOfInterval>=en){
            return segmentTree[node];
        }
        if(en<startOfInterval||start>endOfInterval) return 0;
        int mid=(start+en)/2;
        return getSum(node*2,start,mid,startOfInterval,endOfInterval)+getSum(node*2+1,mid+1,en,startOfInterval,endOfInterval);
    }
    void insert(int node , int start, int en, int index , int value){
        if(en<index||start>index) return ;
        if(start==en&&start==index){
            segmentTree[node]+=value;
            return;
        }
        int mid=(start+en)/2;
        if(start==en)return;
        insert(node*2,start,mid,index,value);
        insert(node*2+1,mid+1,en,index,value);
        segmentTree[node]=segmentTree[node*2]+segmentTree[node*2+1];
    }


    void insert(int index , int value){
        insert(1,1,size,index,value);
    }
};
