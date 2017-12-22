(function (THREE, L3DEditor) {

  'use strict';


  var htmlNode,
    scene,
    camera,
    cameraControl,
    renderer,
    object,
    rotation = 0;

  var getDimensions = function (htmlNode) {
    return {
      width: htmlNode.offsetWidth,
      height: htmlNode.offsetHeight
    };
  };

  function render() {
    if (L3DEditor.Config && L3DEditor.Config.isRotationActive) {
      rotation += 0.005;
      object.rotateY(0.005);
    }
    cameraControl.update();
    renderer.render(scene, camera);
    requestAnimationFrame(render);
  }

  function createRenderer() {
    var renderer = new THREE.WebGLRenderer();
    renderer.setClearColor(0x000000, 1.0);
    var dim = getDimensions(htmlNode);
    renderer.setSize(dim.width, dim.height);
    renderer.shadowMapEnabled = true;
    renderer.shadowMap.type = THREE.PCFSoftShadowMap;
    return renderer;
  }

  function createCamera(sceneToLookAt) {
    var dim = getDimensions(htmlNode);
    var camera = new THREE.PerspectiveCamera(45, dim.width / dim.height, 0.1, 1000);
    camera.position.x = 100;
    camera.position.y = 100;
    camera.position.z = 100;
    camera.lookAt(sceneToLookAt.position);
    cameraControl = new THREE.OrbitControls(camera, htmlNode);
    return camera;
  }

  function createPointLight() {
    var light = new THREE.PointLight(0xffffff, 1, 1000);
    light.position.set(100, 100, 100);
    light.name = 'directional';

    light.castShadow = true;
    light.target = {
      position: {
        x: 0,
        y: 0,
        z: 0
      }
    };
    light.shadow.camera.near = 0.5;
    light.shadow.camera.far = 5000;
    light.shadow.camera.left = -500;
    light.shadow.camera.bottom = -500;
    light.shadow.camera.right = 500;
    light.shadow.camera.top = 500;

    return light;
  }

  function createAmbientLight() {
    var light = new THREE.AmbientLight(0x333333);
    return light;
  }

  function createLight() {
    return createPointLight();
  }

  function show(rawDefinition) {
    try {
      if (object) {
        scene.remove(object);
      }
      var compiledDefinition = L3DEditor.DefinitionService.compile(rawDefinition);
      object = L3DEditor.ConfigurableCompoundMeshBuilder.create(compiledDefinition);
      scene.add(object);
    } catch (e) {
      console.error(e);
    }
  }

  L3DEditor.Room = function(element, definitions) {
    htmlNode = element;

    renderer = createRenderer(htmlNode);

    scene = new THREE.Scene();
    camera = createCamera(scene);

    show(definitions);

    scene.add(createLight());

    htmlNode.append(renderer.domElement);

    render();

    this.update = function (definition) {
      show(definition);
      object.rotateY(rotation);
    }

  };

  return L3DEditor;

})(THREE || {}, L3DEditor || {});
