package util;

/**
 * Created by cuongnb on 07/12/2016.
 */
public class Constant {
    public enum caseTest {
        rankedNode {
            @Override
            public String toString() {
                return "rankedNode";
            }
        },
        normally {
            @Override
            public String toString() {
                return "normally";
            }
        }

    }

    public enum outcome {
        threeOutcome {
            @Override
            public String toString() {
                return "3";
            }
        },
        fiveOutcome {
            @Override
            public String toString() {
                return "5";
            }
        },
        sevenOutcome {
            @Override
            public String toString() {
                return "7";
            }
        }
    }

    public enum threeOutcome {
        Lower {
            @Override
            public String toString() {
                return "Lower";
            }
        },
        Medium {
            @Override
            public String toString() {
                return "Medium";
            }
        },
        Height {
            @Override
            public String toString() {
                return "Height";
            }
        }
    }

    public enum fiveOutcome {
        Lower {
            @Override
            public String toString() {
                return "Lower";
            }
        },
        Medium {
            @Override
            public String toString() {
                return "Medium";
            }
        },
        Height {
            @Override
            public String toString() {
                return "Height";
            }
        }
    }

    public enum sevenOutcome {
        Lower {
            @Override
            public String toString() {
                return "Lower";
            }
        },
        Medium {
            @Override
            public String toString() {
                return "Medium";
            }
        },
        Height {
            @Override
            public String toString() {
                return "Height";
            }
        }
    }

}
