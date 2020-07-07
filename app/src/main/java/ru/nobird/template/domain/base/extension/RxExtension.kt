package ru.nobird.template.domain.base.extension

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

data class RxOptional<out T>(val value: T?) {
    fun <R> map(f: (T) -> R?): RxOptional<R> =
        RxOptional(value?.let(f))
}

fun <T> Observable<RxOptional<T>>.unwrapOptional(): Observable<T> =
    this.filter { it.value != null }.map { it.value }

fun <T> Flowable<RxOptional<T>>.unwrapOptional(): Flowable<T> =
    this.filter { it.value != null }.map { it.value }

fun <T> Single<RxOptional<T>>.unwrapOptional(): Maybe<T> =
    this.filter { it.value != null }.map { it.value }

inline fun <T> Maybe<T>.doCompletableOnSuccess(crossinline completableSource: (T) -> Completable): Maybe<T> =
    flatMap { completableSource(it).andThen(Maybe.just(it)) }

inline fun <T> Single<T>.doCompletableOnSuccess(crossinline completableSource: (T) -> Completable): Single<T> =
    flatMap { completableSource(it).andThen(Single.just(it)) }

fun <T> Iterable<T>.toMaybe(): Maybe<T> =
    firstOrNull()?.let { Maybe.just(it) } ?: Maybe.empty()

fun <T> Single<List<T>>.maybeFirst(): Maybe<T> =
    flatMapMaybe { it.toMaybe() }

/**
 * Empty on error stub in order to suppress errors
 */
val emptyOnErrorStub: (Throwable) -> Unit = {}
