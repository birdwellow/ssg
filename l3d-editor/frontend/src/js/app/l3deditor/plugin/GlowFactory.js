L3DEditor = (function (THREE, L3DEditor) {

  'use strict';


  var vertexShader =
    "    uniform vec3 viewVector;\n" +
    "    uniform float c;\n" +
    "    uniform float p;\n" +
    "    varying float intensity;\n" +
    "    void main()\n" +
    "    {\n" +
    "        vec3 vNormal = normalize( normalMatrix * normal );\n" +
    "        vec3 vNormel = normalize( normalMatrix * viewVector );\n" +
    "        intensity = pow( c - dot(vNormal, vNormel), p );\n" +
    "        gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );\n" +
    "    }";

  var fragmentShader =
    "    uniform vec3 glowColor;\n" +
    "    varying float intensity;\n" +
    "    void main()\n" +
    "    {\n" +
    "        vec3 glow = glowColor * intensity;\n" +
    "        gl_FragColor = vec4( glow, 1.0 );\n" +
    "    }";

  var side = THREE.FrontSide;
  // var side = THREE.BackSide;

  // var blending = THREE.NormalBlending;
  var blending = THREE.AdditiveBlending;


  L3DEditor.GlowFactory = {

    createFromDefinitionAndGeometry: function (definition, mesh, camera) {

      var glowMaterial = new THREE.ShaderMaterial({
        uniforms: {
          "c": {
            type: "f",
            value: definition.glow.c || 0.1
          },
          "p": {
            type: "f",
            value: definition.glow.p || 1.8
          },
          glowColor: {
            type: "c",
            value: new THREE.Color(definition.glow.color || 0xff0000)
          },
          viewVector: {
            type: "v3",
            value: camera.position
          }
        },
        vertexShader:   vertexShader,
        fragmentShader: fragmentShader,
        side: side,
        blending: blending,
        transparent: true
      });

      var glowGeometry = mesh.geometry.clone();
      // var modifier = new THREE.SubdivisionModifier( 2 );
      // modifier.modify(glowGeometry);

      var glow = new THREE.Mesh(glowGeometry, glowMaterial);
      glow.position.set(
        mesh.position.x,
        mesh.position.y,
        mesh.position.z
      );
      glow.scale.multiplyScalar(definition.glow.scale || 1.2);

      return glow;
    }

  };

  return L3DEditor;

}) (THREE || {}, L3DEditor || {});
