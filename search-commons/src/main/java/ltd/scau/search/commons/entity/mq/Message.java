/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package ltd.scau.search.commons.entity.mq;

import org.apache.commons.codec.Charsets;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Map;

public class Message {

    private int queueId;
    private int storeSize;
    private long queueOffset;
    private int sysFlag;
    private long bornTimestamp;
    private long storeTimestamp;
    private String msgId;
    private long commitLogOffset;
    private int reconsumeTimes;
    private long preparedTransactionOffset;

    private String topic;
    private int flag;
    private Map<String, String> properties;
    private String messageBody;

    public static Message fromMessageExt(MessageExt ext) {
        Message m = new Message();

        m.setFlag(ext.getFlag());
        m.setTopic(ext.getTopic());
        m.setMsgId(ext.getMsgId());
        m.setSysFlag(ext.getSysFlag());
        m.setQueueId(ext.getQueueId());
        m.setStoreSize(ext.getStoreSize());
        m.setProperties(ext.getProperties());
        m.setQueueOffset(ext.getQueueOffset());
        m.setBornTimestamp(ext.getBornTimestamp());
        m.setReconsumeTimes(ext.getReconsumeTimes());
        m.setStoreTimestamp(ext.getStoreTimestamp());
        m.setCommitLogOffset(ext.getCommitLogOffset());
        m.setPreparedTransactionOffset(ext.getPreparedTransactionOffset());

        if (ext.getBody() != null) {
            m.setMessageBody(new String(ext.getBody(), Charsets.UTF_8));
        }

        return m;
    }

    @Override
    public String toString() {
        return "Message{" +
                "queueId=" + queueId +
                ", storeSize=" + storeSize +
                ", queueOffset=" + queueOffset +
                ", sysFlag=" + sysFlag +
                ", bornTimestamp=" + bornTimestamp +
                ", storeTimestamp=" + storeTimestamp +
                ", msgId='" + msgId + '\'' +
                ", commitLogOffset=" + commitLogOffset +
                ", reconsumeTimes=" + reconsumeTimes +
                ", preparedTransactionOffset=" + preparedTransactionOffset +
                ", topic='" + topic + '\'' +
                ", flag=" + flag +
                ", properties=" + properties +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public int getStoreSize() {
        return storeSize;
    }

    public void setStoreSize(int storeSize) {
        this.storeSize = storeSize;
    }

    public long getQueueOffset() {
        return queueOffset;
    }

    public void setQueueOffset(long queueOffset) {
        this.queueOffset = queueOffset;
    }

    public int getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(int sysFlag) {
        this.sysFlag = sysFlag;
    }

    public long getBornTimestamp() {
        return bornTimestamp;
    }

    public void setBornTimestamp(long bornTimestamp) {
        this.bornTimestamp = bornTimestamp;
    }

    public long getStoreTimestamp() {
        return storeTimestamp;
    }

    public void setStoreTimestamp(long storeTimestamp) {
        this.storeTimestamp = storeTimestamp;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getCommitLogOffset() {
        return commitLogOffset;
    }

    public void setCommitLogOffset(long commitLogOffset) {
        this.commitLogOffset = commitLogOffset;
    }

    public int getReconsumeTimes() {
        return reconsumeTimes;
    }

    public void setReconsumeTimes(int reconsumeTimes) {
        this.reconsumeTimes = reconsumeTimes;
    }

    public long getPreparedTransactionOffset() {
        return preparedTransactionOffset;
    }

    public void setPreparedTransactionOffset(long preparedTransactionOffset) {
        this.preparedTransactionOffset = preparedTransactionOffset;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
