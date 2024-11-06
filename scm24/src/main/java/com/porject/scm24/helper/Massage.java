package com.porject.scm24.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Massage {

private String content;
 
@Builder.Default
private MassageEnum type= MassageEnum.red;



}

