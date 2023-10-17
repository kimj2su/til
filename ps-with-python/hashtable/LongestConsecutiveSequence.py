def longestConsecutive(nums):
    longest = 0
    num_dict = {}
    for num in nums:
        num_dict[num] = True
    for num in num_dict:
        if num - 1 not in num_dict:  # 조건문을 주어서 케이스가 한번 뿐이다.
            cnt = 1
            target = num + 1
            while target in num_dict:
                target += 1
                cnt += 1
                longest = max(longest, cnt)
    return longest

print(longestConsecutive([100, 4, 200, 1, 3, 2]))

def longestConsecutive2(nums):
    longest = 0
    num_dict = {}
    for num in nums:
        num_dict[num] = True
    for num in num_dict:
        if num - 1 not in num_dict:
            cnt = 1
            target = num + 1
            while target in num_dict:
                target += 1
                cnt += 1
                longest = max(longest, cnt)
    return longest

print(longestConsecutive([100, 4, 200, 1, 3, 2]))
