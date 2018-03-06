package com.zzc.capture.project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wethis.library.base.SwipeBackFragment;
import com.wethis.library.utils.RxBus;
import com.zzc.capture.R;
import com.zzc.capture.event.EditStudentEvent;
import com.zzc.capture.project.activites.TypingActivity;
import com.zzc.capture.project.adapter.StudentAdapter;
import com.zzc.capture.project.db.DaoManager;
import com.zzc.capture.project.db.Student;
import com.zzc.capture.project.db.StudentDao;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者: Zzc on 2018-03-05.
 * 版本: v1.0
 */

public class StudentListFragmnet extends SwipeBackFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_add)
    TextView mAdd;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private StudentDao studentDao;
    private StudentAdapter mAdapter;
    private CompositeSubscription mSubscription;
    private LinearLayoutManager layoutManager;

    public static StudentListFragmnet newInstance() {

        Bundle args = new Bundle();

        StudentListFragmnet fragment = new StudentListFragmnet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_studentlist;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mSubscription = new CompositeSubscription();
        initToolBar(mToolbar);
        studentDao = DaoManager.getInstance(_mActivity).getStudentDao();
        mAdapter = new StudentAdapter();
        setOnEditStudentEventListener();
    }

    private void setOnEditStudentEventListener() {
        Subscription subscribe = RxBus.getInstance().toObservable(EditStudentEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<EditStudentEvent>() {
                    @Override
                    public void call(EditStudentEvent editStudentEvent) {
                        mAdapter.addData(0, editStudentEvent.getStudent());
                        layoutManager.scrollToPositionWithOffset(0, 0);
                    }
                });
        mSubscription.add(subscribe);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        layoutManager = new LinearLayoutManager(_mActivity);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(EditStudentFragment.newInstance());
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_student_attestation:
//                        start(TypingFragment.newInstance(mAdapter.getData().get(position).getId()));
                        Intent intent=new Intent(_mActivity, TypingActivity.class);
                        intent.putExtra("id",mAdapter.getData().get(position).getId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getSutdentList();
    }

    public void getSutdentList() {
        studentDao.queryBuilder()
                .orderDesc(StudentDao.Properties.Id)
                .rx().list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        mAdapter.addData(students);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
