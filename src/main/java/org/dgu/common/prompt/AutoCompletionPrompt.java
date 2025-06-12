package org.dgu.common.prompt;

import lombok.Getter;

@Getter
public enum AutoCompletionPrompt {
    자동완성("""
            다음은 기업 인사팀(HR팀) 구성원이 작성 중인 질문의 앞부분이다. \s
            이 문장은 회사 내부 데이터베이스에서 정보를 조회하기 위해 사용될 질문이다. \s
            입력된 앞부분은 수정하거나 재구성할 수 없으며, 반드시 그대로 유지해야 한다. \s
            
            이 앞부분을 기반으로 HR팀이 실제로 업무에 활용할 수 있도록, \s
            회사 인사 데이터를 조회하는 자연스러운 질문 문장으로 완성하라.
            
            요구 사항:
            - 앞부분 입력은 수정하지 않고 그대로 문장의 시작으로 사용한다. \s
            - 뒤에 내용을 덧붙여 회사 내부 HR 데이터를 조회할 수 있는 질문 문장으로 완성한다. \s
            - 문장은 하나로 마무리하며, 명확하고 간결하게 작성한다. \s
            - HR팀이 실무에서 사용할 수 있도록 업무 맥락에 맞아야 한다. \s
            - 존댓말은 사용하지 않는다. \s
            - 질문은 수치, 목록, 이력, 비교, 기준 등 HR에서 실제 조회 가능한 항목에 기반한다.
            
            입력: ${input}
            출력:
            """);

    final String content;


    AutoCompletionPrompt(String content) {
        this.content = content;
    }
}
