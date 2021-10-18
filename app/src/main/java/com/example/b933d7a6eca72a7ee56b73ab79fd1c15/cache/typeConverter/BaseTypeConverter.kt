package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.typeConverter

abstract class BaseTypeConverter<T> {
    abstract fun fromString(string: String): T

    abstract fun toString(value: T): String
}