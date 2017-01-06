package com.develop.zuzik.fragmentnavigation.exception

/**
 * User: zuzik
 * Date: 1/4/17
 */
class AttemptToUseNavigationFragmentWhenItIsNotCreatedException : RuntimeException("Attempt to use NavigationFragment in wrong state - before onCreateView called")