package com.crazysunj.multityperecyclerviewadapter.helper;

import android.support.v7.util.DiffUtil;

import com.crazysunj.multitypeadapter.entity.HandleBase;
import com.crazysunj.multitypeadapter.entity.MultiHeaderEntity;
import com.crazysunj.multitypeadapter.helper.RecyclerViewAdapterHelper;
import com.crazysunj.multityperecyclerviewadapter.R;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.crazysunj.multityperecyclerviewadapter.helper.SimpleHelper.LEVEL_FIRST;
import static com.crazysunj.multityperecyclerviewadapter.helper.SimpleHelper.LEVEL_FOURTH;
import static com.crazysunj.multityperecyclerviewadapter.helper.SimpleHelper.LEVEL_SENCOND;
import static com.crazysunj.multityperecyclerviewadapter.helper.SimpleHelper.LEVEL_THIRD;
import static com.crazysunj.multityperecyclerviewadapter.helper.SimpleHelper.TYPE_FOUR;
import static com.crazysunj.multityperecyclerviewadapter.helper.SimpleHelper.TYPE_ONE;
import static com.crazysunj.multityperecyclerviewadapter.helper.SimpleHelper.TYPE_THREE;
import static com.crazysunj.multityperecyclerviewadapter.helper.SimpleHelper.TYPE_TWO;

/**
 * description
 * <p>
 * Created by sunjian on 2017/5/6.
 */

public class RxAdapterHelper extends RecyclerViewAdapterHelper<MultiHeaderEntity> {

    public RxAdapterHelper() {

        super(null);
        registerMoudleWithShimmer(TYPE_ONE, LEVEL_FIRST, R.layout.item_first,
                R.layout.item_header, R.layout.layout_default_shimmer_view, R.layout.layout_default_shimmer_header_view);
        registerMoudleWithShimmer(TYPE_TWO, LEVEL_FOURTH, R.layout.item_fourth, R.layout.item_header_img,
                R.layout.layout_default_shimmer_view, R.layout.layout_default_shimmer_header_view);
        registerMoudleWithShimmer(TYPE_THREE, LEVEL_SENCOND, R.layout.item_second, R.layout.item_header_img,
                R.layout.layout_default_shimmer_view, R.layout.layout_default_shimmer_header_view);
        registerMoudleWithShimmer(TYPE_FOUR, LEVEL_THIRD, R.layout.item_third,
                R.layout.item_header_img, R.layout.layout_default_shimmer_view, R.layout.layout_default_shimmer_header_view);

    }

    @Override
    protected void startRefresh(List<MultiHeaderEntity> newData, MultiHeaderEntity newHeader, int type, int refreshType) {

        Flowable.just(new HandleBase<MultiHeaderEntity>(newData, newHeader, type, refreshType))
                .onBackpressureDrop()
                .observeOn(Schedulers.computation())
                .map(new Function<HandleBase<MultiHeaderEntity>, DiffUtil.DiffResult>() {
                    @Override
                    public DiffUtil.DiffResult apply(@NonNull HandleBase<MultiHeaderEntity> handleBase) throws Exception {
                        return handleRefresh(handleBase.getNewData(), handleBase.getNewHeader(), handleBase.getType(), handleBase.getRefreshType());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DiffUtil.DiffResult>() {
                    @Override
                    public void accept(@NonNull DiffUtil.DiffResult diffResult) throws Exception {
                        handleResult(diffResult);
                    }
                });
    }
}
