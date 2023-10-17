def two_sum(nums, target):
    memo = {}
    for index, v in enumerate(nums):
        memo[v] = index
    for i, v in enumerate(nums):
        if i != memo[v]:
            needed_number = target - v
            if needed_number in memo:
                return [i, memo[needed_number]]
    return []


print(two_sum([2,7,11,15], target = 9))
