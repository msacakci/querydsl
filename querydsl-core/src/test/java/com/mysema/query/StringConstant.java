/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query;

import java.util.Locale;

import javax.annotation.Nullable;

import com.mysema.query.types.Constant;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Visitor;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.expr.StringExpression;

/**
 * StringConstant represents String constants
 *
 * @author tiwe
 *
 */
final class StringConstant extends StringExpression implements Constant<String>{

    private static final long serialVersionUID = 5182804405789674556L;
    
    public static StringExpression create(String str){
        return new StringConstant(str);
    }

    private final String constant;

    @Nullable
    private volatile NumberExpression<Integer> length;

    @Nullable
    private volatile StringExpression lower, trim, upper;

    StringConstant(String constant){
        this.constant = constant;
    }

    @Override
    public <R,C> R accept(Visitor<R,C> v, C context) {
        return v.visit(this, context);
    }

    @SuppressWarnings("unchecked")
    @Override
    public StringExpression append(Expression<String> s) {
        if (s instanceof Constant){
            return append(((Constant<String>)s).getConstant());
        }else{
            return super.append(s);
        }
    }

    @Override
    public StringExpression append(String s) {
        return new StringConstant(constant + s);
    }

    @Override
    public SimpleExpression<Character> charAt(int i) {
        return SimpleConstant.create(constant.charAt(i));
    }

    @Override
    public StringExpression concat(String s) {
        return append(s);
    }

    @Override
    public BooleanExpression eq(String s){
        return BooleanConstant.create(constant.equals(s));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }else if (o instanceof Constant){
            return ((Constant)o).getConstant().equals(constant);
        }else{
            return false;
        }
    }

    @Override
    public BooleanExpression equalsIgnoreCase(String str) {
        return BooleanConstant.create(constant.equalsIgnoreCase(str));
    }

    @Override
    public String getConstant() {
        return constant;
    }

    @Override
    public int hashCode() {
        return constant.hashCode();
    }

    @Override
    public BooleanExpression isEmpty(){
        return BooleanConstant.create(constant.isEmpty());
    }

    @Override
    public BooleanExpression isNotEmpty(){
        return BooleanConstant.create(!constant.isEmpty());
    }

    @Override
    public NumberExpression<Integer> length() {
        if (length == null) {
            length = NumberConstant.create(Integer.valueOf(constant.length()));
        }
        return length;
    }

    @Override
    public StringExpression lower() {
        if (lower == null) {
            lower = new StringConstant(constant.toLowerCase(Locale.ENGLISH));
        }
        return lower;
    }

    @Override
    public BooleanExpression matches(String pattern){
        return BooleanConstant.create(constant.matches(pattern));
    }

    @Override
    public BooleanExpression ne(String s){
        return BooleanConstant.create(!constant.equals(s));
    }

    @SuppressWarnings("unchecked")
    @Override
    public StringExpression prepend(Expression<String> s) {
        if (s instanceof Constant){
            return prepend(((Constant<String>)s).getConstant());
        }else{
            return super.prepend(s);
        }
    }

    @Override
    public StringExpression prepend(String s) {
        return new StringConstant(s + constant);
    }

    @Override
    public SimpleExpression<String[]> split(String regex) {
        return SimpleConstant.create(constant.split(regex));
    }

    @Override
    public StringExpression substring(int beginIndex) {
        return new StringConstant(constant.substring(beginIndex));
    }

    @Override
    public StringExpression substring(int beginIndex, int endIndex) {
        return new StringConstant(constant.substring(beginIndex, endIndex));
    }

    @Override
    public StringExpression toLowerCase() {
        return lower();
    }

    @Override
    public StringExpression toUpperCase() {
        return upper();
    }

    @Override
    public StringExpression trim() {
        if (trim == null) {
            trim = new StringConstant(constant.trim());
        }
        return trim;
    }

    @Override
    public StringExpression upper() {
        if (upper == null){
            upper = new StringConstant(constant.toUpperCase(Locale.ENGLISH));
        }
        return upper;
    }
}
