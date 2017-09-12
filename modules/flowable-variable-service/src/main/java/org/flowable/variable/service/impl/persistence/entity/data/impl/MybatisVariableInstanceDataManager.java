/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.variable.service.impl.persistence.entity.data.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowable.engine.common.impl.db.AbstractDataManager;
import org.flowable.engine.common.impl.db.CachedEntityMatcher;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntity;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntityImpl;
import org.flowable.variable.service.impl.persistence.entity.data.VariableInstanceDataManager;
import org.flowable.variable.service.impl.persistence.entity.data.impl.cachematcher.VariableByExecutionIdMatcher;

/**
 * @author Joram Barrez
 */
public class MybatisVariableInstanceDataManager extends AbstractDataManager<VariableInstanceEntity> implements VariableInstanceDataManager {

    protected CachedEntityMatcher<VariableInstanceEntity> variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    @Override
    public Class<? extends VariableInstanceEntity> getManagedEntityClass() {
        return VariableInstanceEntityImpl.class;
    }

    @Override
    public VariableInstanceEntity create() {
        VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
        variableInstanceEntity.setRevision(0); // For backwards compatibility, variables / HistoricVariableUpdate assumes revision 0 for the first time
        return variableInstanceEntity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<VariableInstanceEntity> findVariableInstancesByTaskId(String taskId) {
        return getDbSqlSession().selectList("selectVariablesByTaskId", taskId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<VariableInstanceEntity> findVariableInstancesByTaskIds(Set<String> taskIds) {
        return getDbSqlSession().selectList("selectVariablesByTaskIds", taskIds);
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstancesByExecutionId(final String executionId) {
        return getList("selectVariablesByExecutionId", executionId, variableByExecutionIdMatcher, true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<VariableInstanceEntity> findVariableInstancesByExecutionIds(Set<String> executionIds) {
        return getDbSqlSession().selectList("selectVariablesByExecutionIds", executionIds);
    }

    @Override
    public VariableInstanceEntity findVariableInstanceByExecutionAndName(String executionId, String variableName) {
        Map<String, String> params = new HashMap<>(2);
        params.put("executionId", executionId);
        params.put("name", variableName);
        return (VariableInstanceEntity) getDbSqlSession().selectOne("selectVariableInstanceByExecutionAndName", params);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<VariableInstanceEntity> findVariableInstancesByExecutionAndNames(String executionId, Collection<String> names) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("executionId", executionId);
        params.put("names", names);
        return getDbSqlSession().selectList("selectVariableInstancesByExecutionAndNames", params);
    }

    @Override
    public VariableInstanceEntity findVariableInstanceByTaskAndName(String taskId, String variableName) {
        Map<String, String> params = new HashMap<>(2);
        params.put("taskId", taskId);
        params.put("name", variableName);
        return (VariableInstanceEntity) getDbSqlSession().selectOne("selectVariableInstanceByTaskAndName", params);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<VariableInstanceEntity> findVariableInstancesByTaskAndNames(String taskId, Collection<String> names) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("taskId", taskId);
        params.put("names", names);
        return getDbSqlSession().selectList("selectVariableInstancesByTaskAndNames", params);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<VariableInstanceEntity> findVariableInstanceByScopeIdAndScopeType(String scopeId, String scopeType) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("scopeId", scopeId);
        params.put("scopeType", scopeType);
        return getDbSqlSession().selectList("selectVariableInstancesByScopeIdAndScopeType", params);
    }
    
    @Override
    public VariableInstanceEntity findVariableInstanceByScopeIdAndScopeTypeAndName(String scopeId, String scopeType, String variableName) {
        Map<String, String> params = new HashMap<>(3);
        params.put("scopeId", scopeId);
        params.put("scopeType", scopeType);
        params.put("variableName", variableName);
        return (VariableInstanceEntity) getDbSqlSession().selectOne("selectVariableInstanceByScopeIdAndScopeTypeAndName", params);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<VariableInstanceEntity> findVariableInstanceByScopeIdAndScopeTypeAndNames(String scopeId, String scopeType, Collection<String> variableNames) {
        Map<String, Object> params = new HashMap<>(3);
        params.put("scopeId", scopeId);
        params.put("scopeType", scopeType);
        params.put("variableNames", variableNames);
        return getDbSqlSession().selectList("selectVariableInstanceByScopeIdAndScopeTypeAndNames", params);
    }

}