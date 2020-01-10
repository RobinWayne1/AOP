package service.impl;

import service.UserService;

/**
 * @author Robin
 * @date 2019/11/27 -11:25
 */

public class UserServiceImpl implements UserService
{
    @Override
    public void hello()
    {
        System.out.println("hello world!");

    }

    @Override
    public String hello1()
    {
        System.out.println("hello1");
        return "yes";
    }

    @Override
    public void hello(String word,String word1,String word2)
    {
        System.out.println();
        System.out.println(word+";"+word1+";"+word2);
    }
}
