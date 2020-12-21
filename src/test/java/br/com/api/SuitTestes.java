package br.com.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = {HospitalRepositoryTestes.class, OcupacaoTestes.class})
public class SuitTestes {

}
