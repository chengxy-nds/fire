package com.fire.common.domain;

import java.util.List;

/**
 * @Author: xiaofu
 * @Description:
 */
public class BaiDuFaceDetectResult {

    private int error_code;
    private String error_msg;
    private long log_id;
    private int timestamp;
    private int cached;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getCached() {
        return cached;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * face_num : 1
         * face_list : [{"face_token":"e7fa766b713d09df4f9d5da75b2fcb26","location":{"left":211.31,"top":33.88,"width":79,"height":52,"rotation":-3},"face_probability":0.99,"angle":{"yaw":15.62,"pitch":13.21,"roll":-3.24},"face_shape":{"type":"triangle","probability":0.54},"face_type":{"type":"human","probability":0.97}}]
         */

        private int face_num;
        private List<FaceListBean> face_list;

        public int getFace_num() {
            return face_num;
        }

        public void setFace_num(int face_num) {
            this.face_num = face_num;
        }

        public List<FaceListBean> getFace_list() {
            return face_list;
        }

        public void setFace_list(List<FaceListBean> face_list) {
            this.face_list = face_list;
        }

        public static class FaceListBean {
            /**
             * face_token : e7fa766b713d09df4f9d5da75b2fcb26
             * location : {"left":211.31,"top":33.88,"width":79,"height":52,"rotation":-3}
             * face_probability : 0.99
             * angle : {"yaw":15.62,"pitch":13.21,"roll":-3.24}
             * face_shape : {"type":"triangle","probability":0.54}
             * face_type : {"type":"human","probability":0.97}
             */

            private String face_token;
            private LocationBean location;
            private double face_probability;
            private AngleBean angle;
            private FaceShapeBean face_shape;
            private FaceTypeBean face_type;

            public String getFace_token() {
                return face_token;
            }

            public void setFace_token(String face_token) {
                this.face_token = face_token;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public double getFace_probability() {
                return face_probability;
            }

            public void setFace_probability(double face_probability) {
                this.face_probability = face_probability;
            }

            public AngleBean getAngle() {
                return angle;
            }

            public void setAngle(AngleBean angle) {
                this.angle = angle;
            }

            public FaceShapeBean getFace_shape() {
                return face_shape;
            }

            public void setFace_shape(FaceShapeBean face_shape) {
                this.face_shape = face_shape;
            }

            public FaceTypeBean getFace_type() {
                return face_type;
            }

            public void setFace_type(FaceTypeBean face_type) {
                this.face_type = face_type;
            }

            public static class LocationBean {
                /**
                 * left : 211.31
                 * top : 33.88
                 * width : 79
                 * height : 52
                 * rotation : -3
                 */

                private double left;
                private double top;
                private int width;
                private int height;
                private int rotation;

                public double getLeft() {
                    return left;
                }

                public void setLeft(double left) {
                    this.left = left;
                }

                public double getTop() {
                    return top;
                }

                public void setTop(double top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getRotation() {
                    return rotation;
                }

                public void setRotation(int rotation) {
                    this.rotation = rotation;
                }
            }

            public static class AngleBean {
                /**
                 * yaw : 15.62
                 * pitch : 13.21
                 * roll : -3.24
                 */

                private double yaw;
                private double pitch;
                private double roll;

                public double getYaw() {
                    return yaw;
                }

                public void setYaw(double yaw) {
                    this.yaw = yaw;
                }

                public double getPitch() {
                    return pitch;
                }

                public void setPitch(double pitch) {
                    this.pitch = pitch;
                }

                public double getRoll() {
                    return roll;
                }

                public void setRoll(double roll) {
                    this.roll = roll;
                }
            }

            public static class FaceShapeBean {
                /**
                 * type : triangle
                 * probability : 0.54
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class FaceTypeBean {
                /**
                 * type : human
                 * probability : 0.97
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }
        }
    }
}
