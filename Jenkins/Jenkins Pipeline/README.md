# Declarative Pipeline Syntax
Scripted Pipeline 에서는 node {} 만 지키면 나머지는 자유롭게 지정가능

## Declarative Pipeline 필수 Syntax
```java
pipeline
{
    agent any
    stages
    {
            stage ('stage name')
            {
                steps {
                    ...
                }        
            }
    }
}
```
- pipeline {} 외부에는 자유롭게 groovy 코드 사용 가능

## stages / stage
stages 는 다수개의 stage를 포함 가능
stage는 stages/ parallel /martix / steps 중 하나를 포함 가능
