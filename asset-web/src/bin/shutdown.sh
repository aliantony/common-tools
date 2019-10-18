#! /bin/sh

#======================================================================
# 项目停服shell脚本
# 通过项目名称查找到PID
# 然后kill -9 pid
#
#======================================================================

# 项目名称
APPLICATION="@project.name@"

# 项目启动jar包名称
APPLICATION_JAR="@build.finalName@.jar"

PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2 }')
if [[ -z "$PID" ]]
then
    echo ${APPLICATION} is already stopped
else
    kill  ${PID}
    #等待50秒
    for i in `seq 1 50`; do
     PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2 }')
     if [[ -z "$PID" ]]; then
       echo "stop server success"
       exit 0
     fi
     sleep 1
    done
   #如果等待50秒还没有停止完，直接杀掉
   PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2 }')
   if [[ -z "$PID" ]]; then
       echo ${APPLICATION} stopped successfully
   else
      kill -9 ${PID}
      echo ${APPLICATION} stopped successfully
   fi

fi
