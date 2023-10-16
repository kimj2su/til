# https://leetcode.com/problems/valid-parentheses/description/

def validation(s):
    stack = []
    for i in range (len(s)):
        if s[i] == "(" or s[i] == "[" or s[i] == "{":
            stack.append(s[i])
        else:
            if len(stack) == 0:
                return False
            else:
                currunt = stack.pop()
                if currunt == "(" and s[i] != ")":
                    return False
                elif currunt == "[" and s[i] != "]":
                    return False
                elif currunt == "{" and s[i] != "}":
                    return False
    if len(stack) == 0:
        return True

print(validation("()[]{}"))
