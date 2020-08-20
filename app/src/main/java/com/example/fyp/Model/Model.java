package com.example.fyp.Model;

    public class Model {
        private String image_link,text;

        public Model(){

        }
        public Model(String image_link,String text){
            this.image_link=image_link;
            this.text=text;
        }

        public String getImage_link(){
            return image_link;
        }
        public void setImage_link(String image_link){
            this.image_link=image_link;
        }
        public String getText(){
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

/*       <edmt.dev.advancednestedscrollview.MaxHeightRecyclerView
                            android:id="@+id/card_recycler_view"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content">
                        </edmt.dev.advancednestedscrollview.MaxHeightRecyclerView>
                            // final MaxHeightRecyclerView rv = (MaxHeightRecyclerView) findViewById(R.id.card_recycler_view);
       /// final LinearLayoutManager lm = new LinearLayoutManager(this);
        //rv.setLayoutManager(lm);
        //rv.setAdapter(new MyAdapter(this, modelList));
        //rv.addItemDecoration(new DividerItemDecoration(this, lm.getOrientation()));

      /**  rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isRecyclerViewScrolledToTop = lm.findFirstVisibleItemPosition() == 0
                        && lm.findViewByPosition(0).getTop() == 0;
                if (!isRecyclerViewScrolledToTop && !isShowingCardHeaderShadow) {

                    isShowingCardHeaderShadow = true;
                    showOrhideView(cardHeaderShadow, isShowingCardHeaderShadow);
                } else {
                    isShowingCardHeaderShadow = false;
                    showOrhideView(cardHeaderShadow, isShowingCardHeaderShadow);
                }
            }
        });*/

